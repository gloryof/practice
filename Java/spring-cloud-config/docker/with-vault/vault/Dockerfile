FROM vault:1.3.2

RUN mkdir -p /vault/conf

COPY /conf/boot-config.hcl /vault/conf/boot-config.hcl

CMD ["vault", "server", "-config" ,"/vault/conf/boot-config.hcl"]