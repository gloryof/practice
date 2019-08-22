FROM mongo:4.2.0

COPY ./keyfile/key /etc/mongodb/key

RUN chmod 400 /etc/mongodb/key
RUN chown mongodb:mongodb /etc/mongodb/key