package exception

import java.lang.RuntimeException

class ParserNotExistException(msg: String): RuntimeException(msg)
