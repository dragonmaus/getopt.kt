package us.dragonma.getopt

data class Option(val flag: Char?, val arg: String?) {
    constructor(flag: Char?) : this(flag, null)
    constructor() : this(null)

    override fun toString(): String {
        val string = StringBuilder("Option(")

        if (flag != null) {
            string.append("flag='$flag'")
            if (arg != null) {
                string.append(", arg=\"$arg\"")
            }
        }
        string.append(")")

        return string.toString()
    }
}
