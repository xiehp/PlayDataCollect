<html>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>

<div id="app">
	{{ message }}
</div>

<div id="app-2">
  <span v-bind:title="message">
    鼠标悬停几秒钟查看此处动态绑定的提示信息！
  </span>
</div>

<br/>
<span v-bind:title="message2">
    鼠标悬停几秒钟查看此处动态绑定的提示信息！
  </span>

<br/>
{{ message }}
<br>

<span v-bind:title="messag">
    鼠标悬停几秒钟查看此处动态绑定的提示信息！


	<span v-on="messag"/>
</span>

<br/>
<div id="app-3">
	<p v-if="seen">现在你看到我了</p>
</div>
<br/>
<div id="app-7">
	<ol>
		<!--
		  现在我们为每个 todo-item 提供 todo 对象
		  todo 对象是变量，即其内容可以是动态的。
		  我们也需要为每个组件提供一个“key”，稍后再
		  作详细解释。
		-->
		<todo-item
				v-for="item in groceryList"
				v-bind:todo="item"
				v-bind:key="item.id">
		</todo-item>
	</ol>
</div>


<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br>


<script>
    var app = new Vue({
        el: '#app',
        data: {
            message: 'Hello Vue!'
        }
    })

    var app2 = new Vue({
        el: '#app-2',
        data: {
            message: '页面加载于 ' + new Date().toLocaleDateString(),
            message2: 'aaaa'
        }
    })

    var app3 = new Vue({
        el: '#app-3',
        data: {
            seen: 'true'
        }
    })

    // 定义名为 todo-item 的新组件
    Vue.component('todo-item', {
        // todo-item 组件现在接受一个
        // "prop"，类似于一个自定义特性。
        // 这个 prop 名为 todo。
        props: ['todo'],
        template: '<li>{{ todo.text }}</li>'
    })

    var app7 = new Vue({
        el: '#app-7',
        data: {
            groceryList: [
                { id: 0, text: '蔬菜' },
                { id: 1, text: '奶酪' },
                { id: 2, text: '随便其它什么人吃的东西' }
            ]
        }
    })
</script>

</html>