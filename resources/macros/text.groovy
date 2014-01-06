zenquote = [
    description: 'Retrieve a zen quote from GitHub.',
    expression: { new URL('https://api.github.com/zen').text }
]

date = [
    description: 'A date string in various formats',
    expression: { format = "short" -> (format == "short") ? new Date().format("dd/MM/yy") : new Date().format("dd/MM/yy HH:mm:ss") }
]


stockQuote = [
    description: 'Retrieve a stock quote',
    expression: { symbol -> new XmlSlurper().parseText(new WebService(wsdl: "http://www.webservicex.net/stockquote.asmx?WSDL").client.GetQuote(symbol)) }
]