# !/bin/sh
source ./local-file/local.env

target_dir=/tmp/rest-assured

if [ ! -d $target_dir ]; then
  mkdir $target_dir
fi
cp -a ./config/* $target_dir

drone exec --env-file ./local-file/local.env --trusted
