const http = require('http');

http.createServer(function (req, res) {
  
  let body = "";
  req.on("data", (chunk) => {
    body += chunk.toString();
  }).on("end", () => {
    console.log(body);
    res.writeHead(200, {'Content-Type': 'text/plain'});
    res.end("OK!")
  });
}).listen(8000);