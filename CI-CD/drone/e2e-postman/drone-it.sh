# !/bin/sh
target_dir=/tmp/postman

if [ ! -d $target_dir ]; then
  mkdir $target_dir
fi
cp  ./config/* $target_dir

drone exec --env-file ./local-file/local.env --trusted
