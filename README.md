# Coin-tracker  
App for tracking price of cryptocurrencies and get statistics data about them. On the main fragment is a list of coins ordered by market cap or percentage price change in last 24 hours for coins from top 500 market cap rank. We can find another coins by searching it by name or symbol. After clicking on one item we can see details about the coin and go to website to see more data.  
  
App get data from CoinGeckoApi using RetrofitService and store data by Repository in Room database. It also uses RecyclerView, Data Binding and Fragments. The application uses the MVVM pattern. Data are refreshing constantly every 10 seconds or by swiping to refresh.

## Screenshots from app.  
![Alt text](/screenshots/overview.png?raw=true) &nbsp; &nbsp;![Alt text](/screenshots/detail.png?raw=true)
![Alt text](/screenshots/search.png?raw=true) &nbsp; &nbsp;![Alt text](/screenshots/search_empty.png?raw=true)
![Alt text](/screenshots/overview_dark.png?raw=true) &nbsp; &nbsp;![Alt text](/screenshots/detail_dark.png?raw=true)
