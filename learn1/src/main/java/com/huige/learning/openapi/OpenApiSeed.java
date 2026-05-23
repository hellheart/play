package com.huige.learning.openapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class OpenApiSeed implements CommandLineRunner {

    @Autowired
    private OpenApiMapper mapper;

    @Override
    public void run(String... args) {
        mapper.deleteAll();
            s("一言", "随机返回一句优美的话", "https://v1.hitokoto.cn", "文字");
            s("随机图片", "随机返回一张占位图片", "https://picsum.photos/400/300", "图片");
            s("Cat Facts", "随机返回一条猫咪冷知识", "https://catfact.ninja/fact", "动物");
            s("Dog API", "返回随机狗狗图片", "https://dog.ceo/api/breeds/image/random", "动物");
            s("JSONPlaceholder", "免费的假数据 REST API", "https://jsonplaceholder.typicode.com/posts", "开发测试");
            s("天气查询", "命令行风格天气查询", "https://wttr.in/Beijing?format=3", "天气");
            s("GitHub Search", "搜索GitHub仓库", "https://api.github.com/search/repositories?q=spring", "开发");
            s("Bilibili热门", "B站热门视频排行", "https://api.bilibili.com/x/web-interface/popular?pn=1&ps=10", "视频");
            s("汇率API", "实时汇率查询", "https://api.exchangerate-api.com/v4/latest/CNY", "金融");
            s("IP查询", "查询IP地址归属地", "https://ipapi.co/8.8.8.8/json/", "工具");
            s("二维码生成", "在线生成二维码图片", "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=hello", "工具");
            s("节假日查询", "获取节假日信息", "https://timor.tech/api/holiday/year/2026", "工具");
            s("必应每日壁纸", "获取必应每日一图", "https://bing.biturl.top/?resolution=1920&format=json", "图片");
            s("Unsplash图片", "免费高质量图片(需API Key)", "https://api.unsplash.com/photos/random?client_id=demo", "图片");
            s("GitHub Trending", "GitHub热门项目排行", "https://api.github.com/search/repositories?q=stars:>1000&sort=stars", "开发");
            s("Joke API", "随机返回英文笑话", "https://v2.jokeapi.dev/joke/Any", "文字");
            s("Advice Slip", "随机返回一条人生建议", "https://api.adviceslip.com/advice", "文字");
            s("Kanye Rest", "随机返回Kanye West语录", "https://api.kanye.rest", "文字");
            s("The Dog API", "返回狗狗品种图片和信息", "https://api.thedogapi.com/v1/images/search", "动物");
            s("Fox API", "随机返回狐狸图片", "https://randomfox.ca/floof/", "动物");
            s("Shibe Online", "随机返回柴犬图片", "https://shibe.online/api/shibes?count=3", "动物");
            s("PlaceKitten", "返回指定尺寸的猫咪占位图", "https://placekitten.com/400/300", "图片");
            s("Lorem Picsum", "返回指定尺寸随机图片(多参数)", "https://picsum.photos/v2/list?page=1&limit=10", "图片");
            s("PlaceDog", "返回狗狗占位图片", "https://place.dog/400/300", "图片");
            s("OpenWeatherMap", "全球天气数据(需API Key)", "https://api.openweathermap.org/data/2.5/weather?q=Beijing&appid=demo", "天气");
            s("AirVisual", "全球空气质量查询", "https://api.airvisual.com/v2/city?city=Beijing&state=Beijing&country=China&key=demo", "天气");
            s("国家气象局城市ID", "中国城市天气ID列表", "https://geoapi.qweather.com/v2/city/lookup?location=beijing&key=demo", "天气");
            s("ExchangeRate API", "全球汇率查询(免费额度)", "https://open.er-api.com/v6/latest/CNY", "金融");
            s("CoinGecko", "加密货币行情实时数据", "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=usd", "金融");
            s("Binance API", "币安交易行情(无需认证)", "https://api.binance.com/api/v3/ticker/price?symbol=BTCUSDT", "金融");
            s("Alpha Vantage", "股票外汇加密货币数据", "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=IBM&apikey=demo", "金融");
            s("Rest Countries", "全球国家信息查询", "https://restcountries.com/v3.1/name/china", "地理");
            s("Universities API", "全球大学列表查询", "https://universities.hipolabs.com/search?country=China", "教育");
            s("Genderize", "根据姓名判断性别概率", "https://api.genderize.io?name=john", "工具");
            s("Nationalize", "根据姓名判断国籍概率", "https://api.nationalize.io?name=nathaniel", "工具");
            s("Agify", "根据姓名预测年龄", "https://api.agify.io?name=michael", "工具");
            s("IZone 天气", "和风天气免费API(城市搜索)", "https://geoapi.qweather.com/v2/city/lookup?location=北京&key=demo", "天气");
            s("IP Geolocation", "IP地址地理定位", "https://ipapi.co/json/", "工具");
            s("User Agent Parser", "解析User Agent字符串", "https://httpbin.org/user-agent", "开发测试");
            s("Httpbin", "HTTP请求测试工具集", "https://httpbin.org/get", "开发测试");
            s("Reqres", "模拟REST API(增删改查)", "https://reqres.in/api/users?page=1", "开发测试");
            s("DummyJSON", "模拟JSON数据API", "https://dummyjson.com/products", "开发测试");
            s("FakeStore", "模拟电商数据API", "https://fakestoreapi.com/products", "开发测试");
            s("Pokemon API", "宝可梦全量数据API", "https://pokeapi.co/api/v2/pokemon/pikachu", "游戏");
            s("Deck of Cards", "扑克牌游戏API", "https://deckofcardsapi.com/api/deck/new/draw/?count=2", "游戏");
            s("Open Trivia DB", "开放的问答题目数据库", "https://opentdb.com/api.php?amount=5&category=18", "教育");
            s("NASA APOD", "NASA每日天文图片", "https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY", "科技");
            s("SpaceX API", "SpaceX发射数据查询", "https://api.spacexdata.com/v5/launches/latest", "科技");
            s("Dictionary API", "英文词典查询", "https://api.dictionaryapi.dev/api/v2/entries/en/hello", "教育");
            s("Urban Dictionary", "俚语词典非官方API", "https://api.urbandictionary.com/v0/define?term=hello", "文字");
            s("Numbers API", "数字趣味知识", "https://numbersapi.com/42", "教育");
            s("Open Library", "图书信息查询", "https://openlibrary.org/api/books?bibkeys=ISBN:0451526538&format=json", "教育");
            s("TVMaze", "电视节目信息查询", "https://api.tvmaze.com/search/shows?q=breaking+bad", "娱乐");
            s("TheMealDB", "食谱数据API", "https://www.themealdb.com/api/json/v1/1/search.php?s=chicken", "美食");
            s("Cocktail DB", "鸡尾酒配方API", "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=margarita", "美食");
    }

    private void s(String name, String desc, String url, String cat) {
        OpenApi api = new OpenApi();
        api.setName(name); api.setDescription(desc); api.setUrl(url);
        api.setCategory(cat); api.setMethod("GET"); api.setNeedAuth(0); api.setStatus("active");
        mapper.insert(api);
    }
}
