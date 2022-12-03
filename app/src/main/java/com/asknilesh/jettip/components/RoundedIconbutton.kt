package com.asknilesh.jettip.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoundIconButton(
  modifier: Modifier = Modifier,
  imageVector: ImageVector,
  onClick: () -> Unit,
  tint: Color = Color.Black,
  backGroundColor: Color = MaterialTheme.colorScheme.background,
  elevation: Dp = 4.dp,

  ) {
  Card(
    modifier = modifier
      .padding(4.dp)
      .size(40.dp),
    onClick = onClick,
    shape = CircleShape,
    elevation = CardDefaults.cardElevation(elevation),
    colors = CardDefaults.cardColors(backGroundColor)
  ) {
    Box(modifier = Modifier.fillMaxSize()
      .wrapContentSize(Alignment.Center)) {
      Icon(imageVector = imageVector, contentDescription = "", tint = tint)
    }
  }
}