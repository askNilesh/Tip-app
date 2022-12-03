package com.asknilesh.jettip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asknilesh.jettip.components.InputField
import com.asknilesh.jettip.components.RoundIconButton
import com.asknilesh.jettip.ui.theme.JetTipTheme
import com.asknilesh.jettip.util.calculateTip
import com.asknilesh.jettip.util.calculateTotalPerPerson

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      JetTipApp()
    }
  }
}

@Composable
fun JetTipApp() {
  JetTipTheme {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
      Column {
        MainContent()
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun TopHeaderSection(totalPerPerson: Double = 0.0) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(20.dp)
      .height(150.dp),
    shape = RoundedCornerShape(10.dp),
    colors = CardDefaults.cardColors(Color(0xffE9D7F7))
  ) {
    Column(
      modifier = Modifier.fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Text(text = "Total Per Person", style = MaterialTheme.typography.headlineMedium)
      Text(
        text = "$${String.format("%.2f",totalPerPerson)}",
        style = MaterialTheme.typography.displaySmall,
        fontWeight = FontWeight.ExtraBold
      )
    }

  }
}

@Preview(showBackground = true)
@Composable
fun MainContent() {
  val timAmount = remember {
    mutableStateOf(0.0)
  }
  val totalPerson = remember {
    mutableStateOf(0.0)
  }
  val splitBy = remember {
    mutableStateOf(1)
  }

  BillForm(
    splitByState = splitBy,
    tipAmountState = timAmount,
    totalPerPersonState = totalPerson,
  ) {

  }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun BillForm(
  splitByState: MutableState<Int>,
  tipAmountState: MutableState<Double>,
  totalPerPersonState: MutableState<Double>,
  onValChange: (String) -> Unit = {},
) {
  val totalBillState = remember {
    mutableStateOf("")
  }

  val validState = remember(totalBillState.value) {
    totalBillState.value.trim().isNotEmpty()
  }

  val sliderPositionState = remember {
    mutableStateOf(0f)
  }

  val keyBoardController = LocalSoftwareKeyboardController.current

  val tipPercent = (sliderPositionState.value)

  TopHeaderSection(totalPerPersonState.value)

  Surface(
    modifier = Modifier
      .padding(10.dp)
      .fillMaxWidth(),
    shape = RoundedCornerShape(8.dp),
    border = BorderStroke(width = 1.dp, color = Color.LightGray)
  ) {
    Column {
      InputField(
        valueState = totalBillState, label = "Enter bill",
        onAction = KeyboardActions {
          if (!validState) return@KeyboardActions
          onValChange(totalBillState.value.trim())
          keyBoardController?.hide()
        }
      )

      if (validState) {
        Row(
          modifier = Modifier.padding(10.dp),
          horizontalArrangement = Arrangement.Start,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(text = "Split", modifier = Modifier.align(alignment = Alignment.CenterVertically))
          Spacer(modifier = Modifier.width(20.dp))
          Row(
            modifier = Modifier.padding(horizontal = 3.dp),
            horizontalArrangement = Arrangement.End
          ) {
            RoundIconButton(imageVector = Icons.Default.Remove,
              onClick = {
                splitByState.value = if (splitByState.value > 1) splitByState.value - 1
                else 1

                totalPerPersonState.value = calculateTotalPerPerson(
                  totalBill = totalBillState.value.toDouble(),
                  splitBy = splitByState.value,
                  tipPercent = tipPercent
                )

              })
            Spacer(modifier = Modifier.width(10.dp))
            Text(
              text = "${splitByState.value}",
              modifier = Modifier.align(alignment = Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(10.dp))
            RoundIconButton(imageVector = Icons.Default.Add,
              onClick = {
                splitByState.value += 1

                totalPerPersonState.value = calculateTotalPerPerson(
                  totalBill = totalBillState.value.toDouble(),
                  splitBy = splitByState.value,
                  tipPercent = tipPercent
                )

              })

          }
        }


        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
        ) {
          Text(text = "Tip", modifier = Modifier.align(alignment = Alignment.CenterVertically))
          Spacer(modifier = Modifier.width(20.dp))
          Text(
            text = "$${tipAmountState.value.toInt()}",
            modifier = Modifier.align(alignment = Alignment.CenterVertically)
          )

        }

        Column(
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(text = "${tipPercent.toInt()}%")
          Spacer(modifier = Modifier.height(20.dp))
          Slider(value = sliderPositionState.value,
            valueRange = 0f..100f,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp),
            onValueChange = { newVal ->
              sliderPositionState.value = newVal
              tipAmountState.value =
                calculateTip(
                  totalBill = totalBillState.value.toDouble(),
                  tipPercent = tipPercent
                )

              totalPerPersonState.value = calculateTotalPerPerson(
                totalBill = totalBillState.value.toDouble(),
                splitBy = splitByState.value,
                tipPercent = tipPercent
              )

            })
        }
      } else {
        Box {

        }
      }
    }
  }
}


