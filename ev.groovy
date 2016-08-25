#!/usr/bin/env groovy
@Grapes([
    @Grab(group='ch.qos.logback', module='logback-classic', version='1.0.13'),
    @GrabConfig(systemClassLoader=true)
])

CliBuilder cli = [usage: 'Evaluator [options] [<expression>]']
cli.with {
    c longOpt: 'console', 'console mode'
    h longOpt: 'help', 'usage information'
}

def options = cli.parse(args)

framework.Evaluator evaluator = []

if (options.c) {
    evaluator.console()
} else if (options.h) {
    cli.usage()
}
