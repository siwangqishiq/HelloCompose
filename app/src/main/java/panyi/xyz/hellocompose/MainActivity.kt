package panyi.xyz.hellocompose

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import panyi.xyz.hellocompose.ui.theme.HelloComposeTheme

data class Message(val author: String  , val body: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            HelloComposeTheme{
//                MessageCard(Message("村上春树" , "挪威的森林"))
//            }

            HelloComposeTheme{
                Conversation(SampleData.conversationSample)
            }
        }
    }

    @Composable
    fun MessageCard(msg : Message){
        Row(modifier = Modifier.padding(all = 8.dp)){
            Image(
                painter = painterResource(id = R.drawable.profile_picture),
                contentDescription = "Content profile",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(
                        1.5.dp,
                        color = MaterialTheme.colors.secondaryVariant,
                        shape = CircleShape
                    )
            )
            Spacer(modifier = Modifier.width(8.dp))

            var isExpanded by remember { mutableStateOf(false) }
            val surfaceColor : Color by animateColorAsState(
                if(isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface
            )

            Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
                Text(
                    text = msg.author ,
                    style = MaterialTheme.typography.subtitle2,
                    color = MaterialTheme.colors.secondaryVariant,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Surface(
                    elevation = 1.dp ,
                    shape = MaterialTheme.shapes.medium,
                    color = surfaceColor,
                    modifier = Modifier.animateContentSize().padding(1.dp)
                ){
                    Text(
                        text = msg.body,
                        modifier = Modifier.padding(all = 4.dp),
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.secondaryVariant,
                        maxLines = if(isExpanded) Int.MAX_VALUE else 1
                    )
                }
            }
        }
    }

    @Preview(name="Light Mode")
    @Preview(
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        showBackground = true,
        name = "Dark Mode"
    )

    @Composable
    fun MessageCardPreview() {
        //Text("Hello World 你好世界 !")
        HelloComposeTheme{
            MessageCard(Message("村上春树" , "挪威的森林"))
        }
    }

    @Composable
    fun Conversation(messages : List<Message>){
        LazyColumn {
            items(messages){
                message -> MessageCard(message)
            }
        }
    }

    @Preview
    @Composable
    fun ConversationPreview(){
        Conversation(SampleData.conversationSample);
    }
}