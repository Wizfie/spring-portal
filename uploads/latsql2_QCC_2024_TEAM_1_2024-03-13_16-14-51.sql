SELECT *
FROM dosen AS a
JOIN krs AS b
ON a.nidm=b.nidm
JOIN mata_kuliah AS c
ON b.id_mk=c.id_mk