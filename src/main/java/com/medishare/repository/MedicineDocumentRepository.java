package com.medishare.repository;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.medishare.document.MedicineDocument;

@Repository
public interface MedicineDocumentRepository extends ElasticsearchRepository<MedicineDocument, String> {
    @Query("""
    {
    "bool": {
        "should": [
        {
            "match": {
            "medicineOfficialName": {
                "query": "?0",
                "fuzziness": "2",
                "prefix_length": 0
            }
            }
        }
        ]
    }
    }
    """)
    List<MedicineDocument> searchByMedicineOfficialName(String medicineOfficialName);
}
