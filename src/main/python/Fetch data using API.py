#!/usr/bin/env python
# coding: utf-8

# In[14]:


import pandas as pd
from binance import *
import time
from datetime import datetime,timedelta
from plotly import graph_objs as go


# In[15]:


API_key = "XmR9zkovuZWN34wgTXseIEBmHudVp8azhIHkX1A4eduP2HhPqB3xDJUKK8H6Bc7M"
secret_key = "HCZcHuro1FwPiawp89ffLWrghFshVbBJGQBxgVcx4QQ1EkV16y64UeNXGMeX4gJq"


# In[16]:


client = Client(API_key, secret_key)


# In[17]:


coins = ['BTC','ETH']


# In[18]:


merge = False
today = datetime.today().strftime('%Y-%m-%d')
yesterday = (datetime.today() - timedelta(days=1)).strftime('%Y-%m-%d')
for coin in coins:
    print(f'gathering {coin}...')
    start_str = yesterday
    end_str = today
     
    
    klines = client.get_historical_klines(symbol=f'{coin}USDT', 
                                          interval=client.KLINE_INTERVAL_1MINUTE, 
                                          start_str=start_str,
                                          end_str=end_str)
    cols = ['OpenTime',
            f'{coin}_USD_Open',
            f'{coin}_USD_High',
            f'{coin}_USD_Low',
            f'{coin}_USD_Close',
            f'{coin}-USD_volume',
            'CloseTime',
            f'{coin}-QuoteAssetVolume',
            f'{coin}-NumberOfTrades',
            f'{coin}-TBBAV',
            f'{coin}-TBQAV',
            f'{coin}-ignore']

    coin_df = pd.DataFrame(klines,columns=cols)
    coin_df = coin_df[['OpenTime',f"{coin}_USD_Open",f'{coin}_USD_High',f'{coin}_USD_Low',f"{coin}_USD_Close",'CloseTime']]
    if merge == True:
        all_coins_df = pd.merge(coin_df,all_coins_df,how='inner',on=['OpenTime','CloseTime'])
    else:
        all_coins_df = coin_df
        merge = True


# In[21]:


all_coins_df['OpenTime'] = [datetime.fromtimestamp(ts / 1000) for ts in all_coins_df['OpenTime']]
all_coins_df['CloseTime'] = [datetime.fromtimestamp(ts / 1000) for ts in all_coins_df['CloseTime']]


# In[7]:


# all_coins_df


# In[8]:


# for coin in coins:
#     for col in all_coins_df.columns:
#         if not 'Time' in col:
#             all_coins_df[col] = all_coins_df[col].astype(float)
#
#     fig = go.Figure(data=[go.Candlestick(x=all_coins_df['OpenTime'],
#                     open=all_coins_df[f'{coin}-USD_Open'],
#                     high=all_coins_df[f'{coin}-USD_High'],
#                     low=all_coins_df[f'{coin}-USD_Low'],
#                     close=all_coins_df[f'{coin}-USD_Close'])])
#     fig.update_layout(title=coin,xaxis_rangeslider_visible=False)
#     fig.show()


# In[22]:


# print(type(today))
# print(yesterday)
strToday = strYesterday = ""

for i in range(3):
    strToday += today.split("-")[i]
    strYesterday += yesterday.split("-")[i]
fileName = strYesterday + "_" + strToday
all_coins_df.to_csv(f'C:/Users/DanhHoa/Documents/Final_Project_BigData/data/{fileName}', sep=",", index_label = "id")

