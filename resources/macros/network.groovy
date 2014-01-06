nslookup = [
    description: 'e.g. nslookup(\'example.com\')',
    expression: { InetAddress.getAllByName(it) }
]

reverseLookup = [
    description: 'e.g. reverseLookup([127,0,0,1])',
    expression: { InetAddress.getByAddress((byte[]) it).hostName }
]