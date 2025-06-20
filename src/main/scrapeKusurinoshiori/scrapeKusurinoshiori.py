from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from time import sleep
import bs4 as bs
import re


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

    return medicineList


def main():
    driver = initializeSelenium()
    soup = initializeBS(driver)
    medicineList = getMedicines(soup, driver)

    driver.quit()


if __name__ == "__main__":
    main()