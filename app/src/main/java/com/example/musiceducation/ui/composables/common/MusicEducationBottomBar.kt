package com.example.musiceducation.ui.composables.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musiceducation.R
import com.example.musiceducation.ui.theme.MusicEducationTheme

val bottomBarItems = listOf(
    listOf(
        R.drawable.bottombar_book_unselected,
        R.drawable.bottombar_book_selected,
        R.string.bottombar_book
    ),
    listOf(
        R.drawable.bottombar_forum_unselected,
        R.drawable.bottombar_forum_selected,
        R.string.bottombar_forum
    ),
    listOf(
        R.drawable.bottombar_me_unselected,
        R.drawable.bottombar_me_selected,
        R.string.bottombar_me
    )
)

@Composable
fun MusicEducationBottomBar(
    selectedIndex: Int,
    onSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.hsl(0f, 0f, 0.9f)),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(2.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            bottomBarItems.forEachIndexed { index, item ->
                MusicEducationBottomBarItem(
                    icon = item[0],
                    selectedIcon = item[1],
                    text = item[2],
                    isSelected = index == selectedIndex,
                    onClick = { onSelected(index) },
                    modifier = Modifier.size(52.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(2.dp))
    }

}

@Composable
fun MusicEducationBottomBarItem(
    icon: Int,
    selectedIcon: Int,
    text: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    MusicEducationTheme {
         Column(
             modifier = modifier.clickable { onClick() },
             horizontalAlignment = Alignment.CenterHorizontally,
             verticalArrangement = Arrangement.SpaceAround
         ) {
             if(isSelected) {
                 // 选中状态

                 Image(
                     painter = painterResource(id = selectedIcon),
                     contentDescription = null,
                     modifier = Modifier.size(30.dp)
                 )
                 Text(
                     text = stringResource(id = text),
                     color = MaterialTheme.colorScheme.primary,
                     style = MaterialTheme.typography.titleSmall
                 )


             } else {
                 // 未选中状态

                 Image(
                     painter = painterResource(id = icon),
                     contentDescription = null,
                     modifier = Modifier.size(30.dp)
                 )

                 Text(
                     text = stringResource(id = text),
                     color = MaterialTheme.colorScheme.onPrimary,
                     style = MaterialTheme.typography.titleSmall
                 )
             }
         }
    }

}

@Preview
@Composable
fun MusicEducationBottomBarItemPreview() {
    MusicEducationBottomBarItem(
        icon = R.drawable.bottombar_book_unselected,
        selectedIcon = R.drawable.bottombar_book_selected,
        text = R.string.bottombar_book,
        isSelected = true,
        onClick = {}
    )
}

@Preview
@Composable
fun MusicEducationBottomBarPreview() {
    MusicEducationBottomBar(
        selectedIndex = 0,
        onSelected = {},
        modifier = Modifier
    )
}

