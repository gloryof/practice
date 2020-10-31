package main

import (
	"bytes"
	"encoding/json"
	"io/ioutil"
	"net/http"
	"os"

	"github.com/labstack/echo/v4"
)

// EnvConfig 環境設定
type EnvConfig struct {
	BootPort   string
	TargetHost string
	TargetPort string
}

// Person Person response
type Person struct {
	ID   string `json:"id"`
	Name string `json:"name"`
	Age  int32  `json:"age"`
}

// PersonRequest Person request
type PersonRequest struct {
	Name string `json:"name"`
	Age  int32  `json:"age"`
}

// APIError APIエラー
type APIError struct {
	Message string `json:"message"`
}

func main() {
	cnf := loadEnv()

	bp := "http://" + cnf.TargetHost + ":" + cnf.TargetPort + "/api/person"

	e := echo.New()

	e.GET("/api/person/:id", func(c echo.Context) error {

		var createError = func(message string) error {
			return c.JSON(
				http.StatusInternalServerError,
				APIError{
					Message: message,
				},
			)
		}

		url := bp + "/" + c.Param("id")
		res, err := http.Get(url)
		if err != nil {
			return createError("Can not get from " + url)
		}
		defer res.Body.Close()

		if res.StatusCode != http.StatusOK {
			return createError("Status [ " + res.Status + " ] from " + url)
		}

		body, err := ioutil.ReadAll(res.Body)
		if err != nil {
			return createError("Can read get from " + url)
		}

		var p Person
		err = json.Unmarshal(body, &p)
		if err != nil {
			return createError("Can marshal get from " + url)
		}

		return c.JSON(
			http.StatusOK,
			p,
		)
	})

	e.POST("/api/person", func(c echo.Context) error {

		var createError = func(message string) error {
			return c.JSON(
				http.StatusInternalServerError,
				APIError{
					Message: message,
				},
			)
		}

		req := PersonRequest{}
		c.Bind(&req)

		input, err := json.Marshal(req)
		if err != nil {
			return createError("Marshal rquest error  ")
		}
		url := bp
		res, err := http.Post(
			url,
			"application/json",
			bytes.NewBuffer(input),
		)
		if err != nil {
			return createError("Can not post from " + url)
		}
		defer res.Body.Close()

		if res.StatusCode != http.StatusOK {
			return createError("Status [ " + res.Status + " ] from " + url)
		}

		body, err := ioutil.ReadAll(res.Body)
		if err != nil {
			return createError("Can read get from " + url)
		}

		return c.String(
			http.StatusOK,
			string(body),
		)
	})

	e.Start(":" + cnf.BootPort)
}

func loadEnv() EnvConfig {
	return EnvConfig{
		BootPort:   os.Getenv("BOOT_PORT"),
		TargetHost: os.Getenv("TARGET_HOST"),
		TargetPort: os.Getenv("TARGET_PORT"),
	}
}
