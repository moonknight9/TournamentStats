import kotlin.streams.toList

object MatchUpParser {
    private val rawS21 = javaClass.getResource("s21data.csv")?.readText()
    val matchUpList = (rawS21?: "")
                .split("\t")
                .stream()
                .map(::MatchUp)
                .toList()
}