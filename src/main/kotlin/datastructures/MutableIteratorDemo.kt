package datastructures

fun MutableList<Int>.removeEvenElements() {
    this.removeElementsSatisfying { it % 2 == 0 }
}

fun MutableSet<String>.removeElementsStartingWith(str: String) {
    this.removeElementsSatisfying { it.startsWith(str) }
}

fun <T> MutableIterable<T>.removeElementsSatisfying(predicate: (T) -> Boolean) {
    val iterator = this.iterator()
    while (iterator.hasNext()) {
        if (predicate(iterator.next())) {
            iterator.remove()
        }
    }
}
