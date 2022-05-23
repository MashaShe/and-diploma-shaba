package com.example.and_diploma_shaba.repository

//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import com.example.and_diploma_shaba.dto.Post
//
//class PostRepositoryInMemoryImpl : PostRepository {
//    private var nextId = 1L
//    private var posts = listOf(
//
//        Post(
//            postId = nextId++,
//            postAuthor = "Нетология. Университет интернет-профессий будущего",
//            postContent = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
//            postPublishedDate = "21 мая в 18:36",
//            postLikedByMe = false,
//            // var postLikes: Int = 0,
//            //var rePosts: Int = 15978
//            video = "https://www.youtube.com/watch?v=WhWc3b3KhnY\""
//        ),
//        Post(
//            postId = nextId++,
//            postAuthor = "Нетология. Университет интернет-профессий будущего",
//            postContent = "Знаний хватит на всех: на следующей неделе разбираемся с разработкой мобильных приложений, учимся рассказывать истории и составлять PR-стратегию прямо на бесплатных занятиях \uD83D\uDC47",
//            postPublishedDate = "18 сентября в 10:12",
//            postLikedByMe = false
//        ),
//        Post(
//            postId = nextId++,
//            postAuthor = "Нетология. Университет интернет-профессий будущего",
//            postContent = "Освоение новой профессии — это не только открывающиеся возможности и перспективы, но и настоящий вызов самому себе. Приходится выходить из зоны комфорта и перестраивать привычный образ жизни: менять распорядок дня, искать время для занятий, быть готовым к возможным неудачам в начале пути. В блоге рассказали, как избежать стресса на курсах профпереподготовки → http://netolo.gy/fPD",
//            postPublishedDate = "23 сентября в 10:12",
//            postLikedByMe = false
//        ),
//        Post(
//            postId = nextId++,
//            postAuthor = "Нетология. Университет интернет-профессий будущего",
//            postContent = "Делиться впечатлениями о любимых фильмах легко, а что если рассказать так, чтобы все заскучали \uD83D\uDE34\n",
//            postPublishedDate = "22 сентября в 10:14",
//            postLikedByMe = false,
//            video = "https://www.youtube.com/watch?v=WhWc3b3KhnY\""
//        ),
//        Post(
//            postId = nextId++,
//            postAuthor = "Нетология. Университет интернет-профессий будущего",
//            postContent = "Таймбоксинг — отличный способ навести порядок в своём календаре и разобраться с делами, которые долго откладывали на потом. Его главный принцип — на каждое дело заранее выделяется определённый отрезок времени. В это время вы работаете только над одной задачей, не переключаясь на другие. Собрали советы, которые помогут внедрить таймбоксинг \uD83D\uDC47\uD83C\uDFFB",
//            postPublishedDate = "22 сентября в 10:12",
//            postLikedByMe = false
//        ),
//        Post(
//            postId = nextId++,
//            postAuthor = "Нетология. Университет интернет-профессий будущего",
//            postContent = "\uD83D\uDE80 24 сентября стартует новый поток бесплатного курса «Диджитал-старт: первый шаг к востребованной профессии» — за две недели вы попробуете себя в разных профессиях и определите, что подходит именно вам → http://netolo.gy/fQ",
//            postPublishedDate = "21 сентября в 10:12",
//            postLikedByMe = false
//        ),
//        Post(
//            postId = nextId++,
//            postAuthor = "Нетология. Университет интернет-профессий будущего",
//            postContent = "Диджитал давно стал частью нашей жизни: мы общаемся в социальных сетях и мессенджерах, заказываем еду, такси и оплачиваем счета через приложения.",
//            postPublishedDate = "20 сентября в 10:14",
//            postLikedByMe = false
//        ),
//        Post(
//            postId = nextId++,
//            postAuthor = "Нетология. Университет интернет-профессий будущего",
//            postContent = "Большая афиша мероприятий осени: конференции, выставки и хакатоны для жителей Москвы, Ульяновска и Новосибирска \uD83D\uDE09",
//            postPublishedDate = "19 сентября в 14:12",
//            postLikedByMe = false
//        ),
//        Post(
//            postId = nextId++,
//            postAuthor = "Нетология. Университет интернет-профессий будущего",
//            postContent = "Языков программирования много, и выбрать какой-то один бывает нелегко. Собрали подборку статей, которая поможет вам начать, если вы остановили свой выбор на JavaScript.",
//            postPublishedDate = "19 сентября в 10:24",
//            postLikedByMe = false
//        )
//    )
//    private val data = MutableLiveData(posts)
//
//    override fun getAll(): LiveData<List<Post>> = data
//
//    override fun likeById(id: Long) {
//        data.value = data.value?.map {
//            if (it.postId != id) it else if (!it.postLikedByMe) it.copy(
//                postLikedByMe = !it.postLikedByMe,
//                postLikes = it.postLikes + 1,
//                postSeen = it.postSeen + 1
//            ) else it.copy(postLikedByMe = !it.postLikedByMe, postLikes = it.postLikes - 1, postSeen = it.postSeen + 1)
//        }
//    }
//
//    override fun repostById(id: Long) {
//        data.value = data.value?.map {
//            if (it.postId != id) it else it.copy(rePosts = it.rePosts + 1, postSeen = it.postSeen + 1)
//        }
//    }
//
//    override fun removeById(id: Long) {
//        data.value = data.value?.filter { it.postId != id }
//
//    }
//
//    override fun save(post: Post) {
//        if (post.postId == 0L) {
//            // TODO: remove hardcoded postAuthor & postPublishedDate
//            data.value =
//                data.value.orEmpty()
//                    .toMutableList()
//                    .apply {
//                        add(
//                            0,
//                            post.copy(
//                                postId = nextId++,
//                                postAuthor = "Me",
//                                postLikedByMe = false,
//                                postPublishedDate = "now"
//                            )
//                        )
//                    }
//            return
//        }
//        data.value = data.value?.map {
//            if (it.postId != post.postId) it else it.copy(postContent = post.postContent)
//        }
//    }
//
//    override fun getAllUserPostsById(id: Long): LiveData<List<Post>> {
//        TODO("Not yet implemented")
//    }
//
//}