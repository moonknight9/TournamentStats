// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Stats") {
        App()
    }
}

@Composable
@Preview
fun App() {
    MaterialTheme {
        val column1Weight = .3f
        val column2Weight = .1f
        LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
            buildHeader(column1Weight, column2Weight)
            buildBody(column1Weight, column2Weight)
        }
    }
}

private fun LazyListScope.buildHeader(
    column1Weight: Float,
    column2Weight: Float
) {
    item {
        Row(Modifier.background(Color.Gray)) {
            TableCell(text = "Name", weight = column1Weight)
            TableCell(text = "W", weight = column2Weight)
            TableCell(text = "T", weight = column2Weight)
            TableCell(text = "L", weight = column2Weight)
            TableCell(text = "Goals for", weight = column2Weight)
            TableCell(text = "Goals against", weight = column2Weight)
            TableCell(text = "+/-", weight = column2Weight)
            TableCell(text = "Total", weight = column2Weight)
        }
    }
}

private fun LazyListScope.buildBody(
    column1Weight: Float,
    column2Weight: Float
) {
    val table = getTeamStats().sortedWith(compareByDescending<TeamStats> {it.points}.thenByDescending { it.score })
    items(
        items = table,
        key = { it.teamName }
    ) {
        Row(Modifier.fillMaxWidth()) {
            TableCell(text = it.teamName, weight = column1Weight)
            TableCell(text = it.wins.toString(), weight = column2Weight)
            TableCell(text = it.ties.toString(), weight = column2Weight)
            TableCell(text = it.loses.toString(), weight = column2Weight)
            TableCell(text = it.goalsFor.toString(), weight = column2Weight)
            TableCell(text = it.goalsAgainst.toString(), weight = column2Weight)
            TableCell(text = it.score.toString(), weight = column2Weight)
            TableCell(text = it.points.toString(), weight = column2Weight)
        }
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        Modifier
            .border(1.dp, Color.Black)
            .weight(weight)
            .padding(8.dp)
    )
}

private fun getTeamStats(): List<TeamStats> {
    val matches = MatchUpParser.matchUpList
    val teamNames = matches.flatMap { listOf(it.team1, it.team2) }.distinct()
    return teamNames.map { team ->
        team to matches.filter { it.team1 == team || it.team2 == team }
    }
        .map { TeamStats(it.first, it.second) }
}