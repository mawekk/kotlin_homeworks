package homeworks.homework3

@Suppress("MagicNumber")
fun main() {
    val tree = AVLTree<Int, String>()
    val map = mapOf(
        4 to "meow",
        34 to "purr",
        76 to "buzz",
        195 to "woof",
        62 to "coo",
        10 to "moo",
        18 to "tweet",
        8 to "hiss",
        23 to "roar",
        111 to "oink",
        59 to "neigh",
        198 to "baa",
        31 to "ribbit"
    )
    tree.putAll(map)
    print(tree)
}
