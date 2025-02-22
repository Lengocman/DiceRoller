package com.example.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.diceroller.ui.theme.DiceRollerTheme

// Interface cho xúc xắc
interface Dice {
    fun roll(): Int
}

// Lớp xúc xắc 6 mặt
class SixSidedDice : Dice {
    private val numSides = 6
    override fun roll(): Int {
        return (1..numSides).random()
    }
}

// ViewModel để xử lý logic
class DiceViewModel(private val dice: Dice = SixSidedDice()) : ViewModel() {
    var result by mutableIntStateOf(1)
        private set

    fun rollDice() {
        result = dice.roll()
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DiceRollerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DiceRollerApp(modifier = Modifier.wrapContentSize().padding(innerPadding))
                }
            }
        }
    }
}

@Preview
@Composable
fun DiceRollerApp(modifier: Modifier = Modifier) {
    val viewModel = remember { DiceViewModel() }
    DiceWithButtonAndImage(viewModel = viewModel, modifier = modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)
    )
}

@Composable
fun DiceWithButtonAndImage(viewModel: DiceViewModel, modifier: Modifier = Modifier) {
    val imageResource = when (viewModel.result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(imageResource), contentDescription = viewModel.result.toString())
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.rollDice() }) {
            Text(stringResource(R.string.roll))
        }
    }
}