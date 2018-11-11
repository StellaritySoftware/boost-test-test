package pages

/**
 * Created by Kateryna on 04.11.2017.
 */
class Config {
    static user = System.getProperty('user')
    static password = System.getProperty('password')

    static def testFiles = getClass()
            .getResource('/testFiles')
            .toURI()

}
