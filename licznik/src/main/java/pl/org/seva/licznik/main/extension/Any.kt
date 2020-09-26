package pl.org.seva.licznik.main.extension

import pl.org.seva.licznik.main.init.instance
import java.util.logging.Logger

val Any.log get() = instance<String, Logger>(arg = this::class.java.name)