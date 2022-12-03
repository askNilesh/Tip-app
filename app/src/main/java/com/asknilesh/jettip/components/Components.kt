package com.asknilesh.jettip.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons.Rounded
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InputField(
  modifier: Modifier = Modifier.fillMaxWidth(),
  valueState: MutableState<String>,
  label: String,
  enabled: Boolean = true,
  singleLine: Boolean = true,
  keyBoardType: KeyboardType = KeyboardType.Number,
  imeAction: ImeAction = ImeAction.Done,
  onAction: KeyboardActions = KeyboardActions.Default,
  textStyle: TextStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground)
) {
  OutlinedTextField(
    modifier = modifier.padding(10.dp),
    value = valueState.value,
    onValueChange = {
      valueState.value = it
    },
    label = {
      Text(text = label)
    },
    enabled = enabled,
    singleLine = singleLine,
    leadingIcon = {
      Icon(imageVector = Rounded.AttachMoney, contentDescription = "Money icon")
    },
    textStyle = textStyle,
    keyboardOptions = KeyboardOptions(
      keyboardType = keyBoardType,
      imeAction = imeAction
    ),
    keyboardActions = onAction
  )
}