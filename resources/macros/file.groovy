save = [
    description: 'Save the specified binary data to a file.',
    expression: { byte[] data, filename -> new File(filename).withOutputStream { out ->
           out << data
        }
    }
]
