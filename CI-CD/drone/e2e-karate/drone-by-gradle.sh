# !/bin/sh
source ./local-file/local.env

target_dir=/tmp/e2e-karate-gradle

if [ ! -d $target_dir ]; then
  mkdir $target_dir
fi
cp -a ./src/test/resources/* $target_dir

drone exec --env-file ./local-file/local.env --trusted
