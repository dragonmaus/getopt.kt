package us.dragonma.getopt

class GetOpt(private val args: Array<String>, optstring: String) : Iterator<Option> {
    var index = 0
        // `point` must be reset to 0 whenever `index` is changed
        set(value) {
            field = value
            this.point = 0
        }

    private var point = 0
    private val opts = parseOptString(optstring)
    private var next: Option? = null

    private fun parseOptString(optstring: String): Map<Char, Boolean> {
        val map: MutableMap<Char, Boolean> = HashMap()

        var i = 0
        while (i < optstring.length) {
            if (i + 1 < optstring.length && optstring[i + 1] == ':') {
                map[optstring[i]] = true
                i++
            } else {
                map[optstring[i]] = false
            }
            i++
        }

        return map
    }

    @Throws(GetOptException::class)
    private fun consume(): Option {
        if (this.point == 0) {
            /*
             * Rationale excerpts below taken verbatim from "The Open Group Base Specifications
             * Issue 7, 2018 edition", IEEE Std 1003.1-2017 (Revision of IEEE Std 1003.1-2008).
             * Copyright © 2001-2018 IEEE and The Open Group.
             */

            /*
             * If, when getopt() is called:
             *      argv[optind]    is a null pointer
             *      *argv[optind]   is not the character '-'
             *      argv[optind]    points to the string "-"
             * getopt() shall return -1 without changing optind.
             */
            if (this.index >= this.args.size || !this.args[this.index].startsWith('-') || this.args[this.index] == "-") {
                return Option()
            }

            /*
             * If:
             *      argv[optind]    points to the string "--"
             * getopt() shall return -1 after incrementing optind.
             */
            if (this.args[this.index] == "--") {
                this.index++
                return Option()
            }

            this.point++
        }

        val opt: Char = this.args[this.index][this.point++]
        when (this.opts[opt]) {
            null -> throw GetOptException("unknown option -- '$opt'")

            false -> {
                if (this.point >= this.args[this.index].length) {
                    this.index++
                }

                return Option(opt)
            }

            true -> {
                val arg: String = if (this.point >= this.args[this.index].length) {
                    this.index++
                    if (this.index >= this.args.size) {
                        throw GetOptException("option requires an argument -- '$opt'")
                    }
                    this.args[this.index]
                } else {
                    this.args[this.index].drop(this.point)
                }
                this.index++

                return Option(opt, arg)
            }
        }
    }

    /**
     * Returns `true` if the iteration has more elements.
     */
    @Throws(GetOptException::class)
    override fun hasNext(): Boolean {
        if (this.next == null) {
            this.next = this.consume()
        }

        return this.next!!.flag != null
    }

    /**
     * Returns the next element in the iteration.
     */
    @Throws(GetOptException::class)
    override fun next(): Option {
        if (this.next == null) this.next = this.consume()

        val current = this.next!!
        this.next = null

        return current
    }
}
