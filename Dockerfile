FROM postgres:16

COPY kgpc.sql /docker-entrypoint-initdb.d/