import json
import mysql.connector

db_config = {
    'host': 'localhost',
    'user': 'root',
    'password': 'Takaki1107',
    'database': 'medishare'
}

def insertMedicineData():
    with open('../../../scrapeKusurinoshiori/medicine.json', 'r', encoding='utf-8') as file:
        medicines = json.load(file)

    conn = mysql.connector.connect(**db_config)
    cursor = conn.cursor()

    try:
        for medicine in medicines:
            sql = """
            INSERT IGNORE INTO medicine (medicine_official_name, url_kusurino_shiori)
            VALUES (%s, %s)
            """
            values = (medicine['medicineOfficialName'], medicine['urlKusurinoShiori'])
            cursor.execute(sql, values)
            
    except mysql.connector.Error as err:
        print(f"Error: {err}")

    conn.commit()
    cursor.close()
    conn.close()
    print("medicineデータベースへのデータ挿入完了")

def main():
    insertMedicineData()

if __name__ == "__main__":
    main()