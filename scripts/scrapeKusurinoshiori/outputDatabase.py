import json
import mysql.connector

db_config = {
    'host': 'localhost',
    'user': 'medishare_user',
    'password': 'medishare_user',
    'database': 'medishare'
}

def insertMedicineData():
    with open('./medicine.json', 'r', encoding='utf-8') as file:
        medicines = json.load(file)

    conn = mysql.connector.connect(**db_config)
    cursor = conn.cursor()

    try:
        for medicine in medicines:
            sql = """
            INSERT IGNORE INTO official_medicine (medicine_official_name, url_kusurinoshiori)
            VALUES (%s, %s)
            """
            values = (medicine['medicineOfficialName'], medicine['urlKusurinoShiori'])
            cursor.execute(sql, values)
        
        print("official_medicineテーブルにデータを追加しました")
    except mysql.connector.Error as err:
        print(f"Error: {err}")

    conn.commit()
    cursor.close()
    conn.close()

def main():
    insertMedicineData()

if __name__ == "__main__":
    main()