package com.ibrahim.words_parsing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ibrahim.words_parsing.words_count_feature.presentation.viewmodel.WordsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var wordsViewModel: WordsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wordsViewModel.getWords()

        val v = "my   extra spaced                        string".replace("\\s+?"," ")
        v


        var html = "  doctype html   html lang  ar  data n head   7B 22lang 22  7B 221 22  22ar 22 7D 7D      head       meta data n head  1  charset  utf 8   meta data n head  1  name  viewport  content  width device width initial scale 1   link data n head  1  rel  icon  type  image x icon  href   icon png   base href      link rel  preload  href    nuxt 0255ea5 js  as  script   link rel  preload  href    nuxt 191e23f js  as  script   link rel  preload  href    nuxt 418e0fa js  as  script   link rel  preload  href    nuxt 7c1cfc2 js  as  script      script           function w d s l i  w l  w l      w l  push   gtm start           new Date   getTime   event  gtm js    var f d getElementsByTagName s  0           j d createElement s  dl l   dataLayer    l   l    j async true j src           https   www googletagmanager com gtm js id   i dl f parentNode insertBefore j f              window document  script   dataLayer   GTM NQS9BZB          script      head     body       div id    nuxt   style  nuxt loading background  fff visibility hidden opacity 0 position absolute left 0 right 0 top 0 bottom 0 display flex justify content center align items center flex direction column animation nuxtLoadingIn 10s ease  webkit animation nuxtLoadingIn 10s ease animation fill mode forwards overflow hidden  keyframes nuxtLoadingIn 0  visibility hidden opacity 0 20  visibility visible opacity 0 100  visibility visible opacity 1    webkit keyframes nuxtLoadingIn 0  visibility hidden opacity 0 20  visibility visible opacity 0 100  visibility visible opacity 1   nuxt loading div  nuxt loading div after border radius 50  width 5rem height 5rem  nuxt loading div font size 10px position relative text indent  9999em border  5rem solid  f5f5f5 border left  5rem solid  d3d3d3  webkit transform translateZ 0   ms transform translateZ 0  transform translateZ 0   webkit animation nuxtLoading 1 1s infinite linear animation nuxtLoading 1 1s infinite linear  nuxt loading error div border left  5rem solid  ff4500 animation duration 5s   webkit keyframes nuxtLoading 0   webkit transform rotate 0  transform rotate 0  100   webkit transform rotate 360deg  transform rotate 360deg    keyframes nuxtLoading 0   webkit transform rotate 0  transform rotate 0  100   webkit transform rotate 360deg  transform rotate 360deg     style  script window addEventListener  error  function   var e document getElementById  nuxt loading   e   e className    error      script  div id  nuxt loading  aria live  polite  role  status   div Loading     div   div   div  script window   NUXT    config   app  basePath     assetsPath    nuxt   cdnURL null     script     iframe src  https   www googletagmanager com ns html id GTM NQS9BZB  height  0  width  0  style  display none visibility hidden    iframe     script src    nuxt 0255ea5 js    script  script src    nuxt 191e23f js    script  script src    nuxt 418e0fa js    script  script src    nuxt 7c1cfc2 js    script   body    html  "

        var str = "Kotlin    Is    Awesome"

        str = str.replace("\\s+".toRegex(), " ")

        html = html.replace("\\s+".toRegex(), " ")
        html

        val newText =
            html.replace("\\s+".toRegex(), " ")

        newText

        str
    }
}