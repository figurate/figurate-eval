Figurate - Evaluation Scripting
===================================

An exploration in configuration management DSLs. Primarily focused on support confguration of OSGi constellations, but
may apply to a more general context in the future..


## Configurable targets:

* File - create, delete, update file contents and permissions
* Exec - execute a command
* Package - package management (via yum, apt, homebrew, etc.)
* User - user management
* XML - parse, manipulate, output
* JSON - parse, generate
* HTTP - parse and generate requests
* ...

## Pipeline

A pipeline is a chain of evaluations producing a composite result. 

### Examples:

```
image('/tmp/map-700-collins-st-melbourne', border: 'shadow') << map << '700 Collins St, Melbourne'
```

```
pdf("/tmp/screenshot-${now.dateTime}.pdf") << ['This is a description of the screenshot', screenshot()]
```

## Enclosures

Enclosures wrap an evaluation to provide support for external management of evaluations. Some example enclosures are:

* timeout(TimeUnit) - support for timeout of long-running evaluations
* dir(File) - execute in the specified directory
* parallel(Closure...) - execute multiple evaluations concurrently
* retry(int) - retry up to limit times
* waitFor(Closure) - wait for a condition before executing

## Concepts:

* Evaluation - groups an input and a result. e.g. Eval[input: 2 + 2, result: 4]
* Synonym - an alias for an input template. e.g. Syn[name: 'avg', input: { it.sum() / it.size() }]
* ...


