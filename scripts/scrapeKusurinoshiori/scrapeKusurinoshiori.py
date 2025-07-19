from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from time import sleep
import bs4 as bs
import re
import json
import csv


url = "https://www.rad-ar.or.jp/siori/search?dj0xMDAmcj1rJms9dCZwPTEmZz0wJnc9"


class MEDICINE:
    def __init__(self, medicineOfficialName, urlKusurinoShiori):
        self.medicineOfficialName = medicineOfficialName
        self.urlKusurinoShiori = urlKusurinoShiori

    def toDict(self):
        return {
            "medicineOfficialName": self.medicineOfficialName,
            "urlKusurinoShiori": self.urlKusurinoShiori
        }
    
    def __repr__(self):
        return f"MEDICINE(name='{self.medicineOfficialName}', url='{self.urlKusurinoShiori}')"

def waitForPageLoad(driver):
    try:
        WebDriverWait(driver, 10).until(
            EC.presence_of_element_located((By.CLASS_NAME, "c-search-results"))
        )
    except:
        print("検索結果が表示されませんでした: EC.presence_of_element_located((By.CLASS_NAME, 'c-search-results'))")
        driver.quit()
        exit()

    try:
        WebDriverWait(driver, 10).until(
            EC.presence_of_element_located((By.CLASS_NAME, "c-pagenation")) # 誤字はそのまま
        )
    except:
        print("検索結果が表示されませんでした: EC.presence_of_element_located((By.CLASS_NAME, 'c-pagenation'))") # 誤字はそのまま
        driver.quit()
        exit()

def initializeSelenium():
    options = Options()
    options.add_argument('--headless')
    options.add_argument('--log-level=3')
    options.add_argument('--disable-gpu')
    options.add_argument('--no-sandbox')
    driver = webdriver.Chrome(options = options)
    driver.get(url)
    waitForPageLoad(driver)
    return driver


def initializeBS(driver):
    html = driver.page_source
    soup = bs.BeautifulSoup(html, "html.parser")
    return soup


def getNumberOfPages(driver):
    try:
        element = driver.find_element(By.TAG_NAME, "app-pagenation")
        numberOfMedicines = int(element.get_attribute("data-total"))
        per_page = int(element.get_attribute("data-per-page"))

        if numberOfMedicines % 100 == 0:
            pages = numberOfMedicines // 100
        else:
            pages = numberOfMedicines // 100 + 1

        print(f"薬の数: {numberOfMedicines}")
        print(f"1ページ当たりの薬の数: {per_page}")
        print(f"総ページ数: {pages}")
        return pages
    except:
        print("ページ数が取得できませんでした")
        driver.quit()
        exit()


def getMedicines(soup, driver):
    medicineList = []
    pageCnt = 1
    pages = getNumberOfPages(driver)

    while(pageCnt <= pages):
        print(f"{pageCnt}ページ目を処理中...")
        medicines = soup.find("div", {"class": "c-search-results"}).find_all("div", {"class": "c-search-result"})

        for medicine in medicines:
            medicineOfficialName = medicine.find("div", {"class": "c-search-result__name"}).text
            urlKusurinoShiori = medicine.find("a", {"class": "c-serch-result__link"}).get("href") #誤字はそのまま
            medicineList.append(MEDICINE(medicineOfficialName, "https://www.rad-ar.or.jp/siori/" + urlKusurinoShiori))
        
        print(f"データの数: {medicineList.__len__()}")

        if pageCnt < pages:
            url = None

            for label in soup.find_all("div", {"class" : "c-button__label"}):
                if re.match(r"^\d+$", label.text.strip()) and label.text.strip() == str(pageCnt + 1):
                    url = label.find_parent("a").get("href")
                    print(f"次のページへ移動します: {url}")
                    break
            
            if url == None:
                print("次のページへ移動できませんでした")
                driver.quit()
                exit()

            driver.get(url)
            waitForPageLoad(driver)
            soup = initializeBS(driver)
            pageCnt += 1

        elif pageCnt == pages:
            print("最終ページに到達しました")
            break

    return medicineList


def outputJson(medicineList):
    try:
        with open("./scripts/scrapeKusurinoshiori/medicine.json", "w", encoding="utf-8") as f:
            json.dump([medicine.toDict() for medicine in medicineList], f, ensure_ascii=False, indent=4)
        f.close()
        print("kusurinoshiori.jsonに出力しました")
    except Exception as e:
        print(f"JSONファイルの出力に失敗しました: {e}")
        exit()


def outputCsv(medicineList):
    try:
        with open("./scripts/scrapeKusurinoshiori/medicine.csv", "w", encoding="utf-8", newline='') as f:
            writer = csv.writer(f)
            writer.writerow(["medicineOfficialName", "urlKusurinoShiori"])

            for medicine in medicineList:
                writer.writerow([medicine.medicineOfficialName, medicine.urlKusurinoShiori])

        f.close()
        print("kusurinoshiori.csvに出力しました")
    except Exception as e:
        print(f"CSVファイルの出力に失敗しました: {e}")
        exit()


def main():
    driver = initializeSelenium()
    soup = initializeBS(driver)
    medicineList = getMedicines(soup, driver)
    driver.quit()
    outputJson(medicineList)
    outputCsv(medicineList)
    print("すべての処理が完了しました")


if __name__ == "__main__":
    main()