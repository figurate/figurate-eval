nslookup = [
    expression: { InetAddress.getAllByName(it) }
]

reverseLookup = [
    expression: { InetAddress.getByAddress((byte[]) it).hostName }
]