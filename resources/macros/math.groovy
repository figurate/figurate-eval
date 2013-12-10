avg = [
    expression: { it.sum() / it.size() }
]

median = [
    expression: { it.size() % 2 == 0 ? it[it.size() / 2 - 1 as int] : it[(it.size() + 1) / 2 - 1 as int] }
]

mode = [
    expression: { vals -> vals.max { vals.count(it) } }
]

range = [
    expression: { it.max() - it.min() }
]
