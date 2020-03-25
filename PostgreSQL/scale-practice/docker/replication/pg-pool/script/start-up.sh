#! /bin/sh

/var/run/pgpool/pgpool.pid
pgpool -n -f /config/pgpool.conf -F /config/pcp.conf -a /config/pool_hba.conf