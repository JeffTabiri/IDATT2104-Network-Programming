
const http = require('http');

const server = http.createServer((req, res) => {
    // Set the HTTP header
    res.writeHead(200, { 'Content-Type': 'text/html; charset=utf-8' });

    const htmlResponse = `
    <HTML><BODY>
      <H1>Hilsen. Du har koblet deg opp til min enkle web-tjener</H1>
      Header fra klient er:
      <UL>
        ${Object.entries(req.headers).map(([key, value]) => `<LI>${key}: ${value}</LI>`).join('')}
      </UL>
    </BODY></HTML>
  `;


    res.end(htmlResponse);
});
;

server.listen(PORT, () => {
    console.log(`Server running at http://localhost:8080/`);
});

