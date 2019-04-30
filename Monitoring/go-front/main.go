package main

import (
	"flag"
	"go-front/conf/web"
	"os"

	"github.com/labstack/gommon/log"
)

var (
	paramC = flag.String("c", "", "Config file path")
)

func main() {

	flag.Parse()

	c, er := web.CreateWebContainer(*paramC)
	if er != nil {

		log.Errorf("Error!: %+v\n", er)
		os.Exit(1)
	}

	web.StartServer(c)
}

func handleError(err error) {

	log.Errorf("Error!: %+v\n", err)
	os.Exit(1)
}
