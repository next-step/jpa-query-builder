package exception

import java.lang.RuntimeException

class ColumnTypeUnavailableException(msg: String): RuntimeException(msg)
