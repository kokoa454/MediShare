from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from time import sleep
import bs4 as bs


class MEDICINE:
    def __init__(self, medicineOfficialName, urlKusurinoShiori):
        self.medicineOfficialName = medicineOfficialName
        self.urlKusurinoShiori = urlKusurinoShiori

    def toDict(self):
        return {
            "medicineOfficialName": self.medicineOfficialName,
            "urlKusurinoShiori": self.urlKusurinoShiori
        }

url = "https://www.rad-ar.or.jp/siori/search?dj0xMDAmcj1rJms9dCZwPTEmZz0wJnc9"

options = Options()
options.add_argument('--headless')
driver = webdriver.Chrome(options = options)
driver.get(url)

try:
    WebDriverWait(driver, 10).until(
        EC.presence_of_element_located((By.CLASS_NAME, "c-search-results"))
    )
except:
    print("検索結果が表示されませんでした")
    driver.quit()
    exit()

html = driver.page_source
soup = bs.BeautifulSoup(html, "html.parser")

medicineList = []
medicines = soup.find("div", {"class": "c-search-results"}).find_all("div", {"class": "c-search-result"})

for medicine in medicines:
    medicineOfficialName = medicine.find("div", {"class": "c-search-result__name"}).text
    urlKusurinoShiori = medicine.find("a", {"class": "c-search-result__link"}).get("href")
    medicineList.append(MEDICINE(medicineOfficialName, urlKusurinoShiori))

print(medicineList)

driver.quit()
