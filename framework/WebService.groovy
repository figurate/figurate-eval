import groovyx.net.ws.WSClient

@Grapes([
    @Grab(group='org.codehaus.groovy.modules', module='groovyws', version='0.5.2')
])
class WebService {
    String name
    def wsdl
    @Lazy def client = { def c = new WSClient(wsdl, this.class.classLoader); c.initialize(); c }()

    String toString() {
        return name
    }
}
