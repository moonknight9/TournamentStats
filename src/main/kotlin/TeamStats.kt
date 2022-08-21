class TeamStats(val teamName: String, matchUps: List<MatchUp>) {
    val goalsFor = matchUps.map { if (it.team1 == teamName) it.team1Score else it.team2Score }.reduce { acc, i ->  acc + i}
    val goalsAgainst = matchUps.map { if (it.team1 == teamName) it.team2Score else it.team1Score }.reduce { acc, i ->  acc + i}
    val score = goalsFor - goalsAgainst
    val points = matchUps.map { it.getScore(teamName).value }.reduce { acc, i -> acc + i }
    val wins = matchUps.count { it.getScore(teamName) == MatchUp.MatchOutcomes.WIN }
    val loses = matchUps.count { it.getScore(teamName) == MatchUp.MatchOutcomes.LOSS }
    val ties = matchUps.count { it.getScore(teamName) == MatchUp.MatchOutcomes.TIE }
    override fun toString(): String {
        return "$teamName goalsFor=$goalsFor goalsAgainst=$goalsAgainst score=$score points=$points"
    }

}