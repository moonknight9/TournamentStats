data class MatchUp(private val matchRawString: String) {
    private val teamsArray = matchRawString.split("\n")

    val team1 = teamsArray[0].filterNot { it.isDigit() }.trim()
    val team1Score = teamsArray[0].filter { it.isDigit() }.toIntOrNull()?: 0

    val team2 = teamsArray[1].filterNot { it.isDigit() }.trim()
    val team2Score = teamsArray[1].filter { it.isDigit() }.toIntOrNull()?: 0

    override fun toString(): String {
        return "MatchUp(team1='$team1', team1Score='$team1Score', team2='$team2', team2Score='$team2Score')"
    }

    enum class MatchOutcomes(val value: Int) {
        WIN(3),
        LOSS(0),
        TIE(1),
        NOT_PLAYED(0)
    }

    fun getScore(teamName: String): MatchOutcomes {
        if (team1Score == team2Score) {
            if (team1Score == 0) {
                return MatchOutcomes.NOT_PLAYED
            }
            return MatchOutcomes.TIE
        }
        return if ((team1 == teamName && team1Score > team2Score) || (team2 == teamName && team2Score > team1Score)) {
            MatchOutcomes.WIN
        } else {
            MatchOutcomes.LOSS
        }
    }
}
