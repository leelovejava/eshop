package com.roncoo.eshop.cache.service.impl;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisCluster;

import com.roncoo.eshop.cache.hystrix.command.GetProductInfoFromRedisCacheCommand;
import com.roncoo.eshop.cache.hystrix.command.GetShopInfoFromRedisCacheCommand;
import com.roncoo.eshop.cache.hystrix.command.SaveProductInfo2RedisCacheCommand;
import com.roncoo.eshop.cache.hystrix.command.SaveShopInfo2RedisCacheCommand;
import com.roncoo.eshop.cache.model.ProductInfo;
import com.roncoo.eshop.cache.model.ShopInfo;
import com.roncoo.eshop.cache.service.CacheService;

/**
 * 缓存Service实现类
 * @author Administrator
 *
 */
@Service("cacheService")
public class CacheServiceImpl implements CacheService {
	
	public static final String CACHE_NAME = "local";
	
	@Resource
	private JedisCluster jedisCluster;
	
	/** 
	 * 将商品信息保存到本地缓存中
	 * @param productInfo
	 * @return
	 */
	@CachePut(value = CACHE_NAME, key = "'key_'+#productInfo.getId()")
	public ProductInfo saveLocalCache(ProductInfo productInfo) {
		return productInfo;
	}
	
	/**
	 * 从本地缓存中获取商品信息
	 * @param id 
	 * @return
	 */
	@Cacheable(value = CACHE_NAME, key = "'key_'+#id")
	public ProductInfo getLocalCache(Long id) {
		return null;
	}
	
	/**
	 * 将商品信息保存到本地的ehcache缓存中
	 * @param productInfo
	 */
	@CachePut(value = CACHE_NAME, key = "'product_info_'+#productInfo.getId()")
	public ProductInfo saveProductInfo2LocalCache(ProductInfo productInfo) {
		return productInfo;
	}
	
	/**
	 * 从本地ehcache缓存中获取商品信息
	 * @param productId
	 * @return
	 */
	@Cacheable(value = CACHE_NAME, key = "'product_info_'+#productId")
	public ProductInfo getProductInfoFromLocalCache(Long productId) {
		return null;
	}
	
	/**
	 * 将店铺信息保存到本地的ehcache缓存中
	 * @param productInfo
	 */
	@CachePut(value = CACHE_NAME, key = "'shop_info_'+#shopInfo.getId()")
	public ShopInfo saveShopInfo2LocalCache(ShopInfo shopInfo) {
		return shopInfo;
	}
	
	/**
	 * 从本地ehcache缓存中获取店铺信息
	 * @param productId
	 * @return
	 */
	@Cacheable(value = CACHE_NAME, key = "'shop_info_'+#shopId")
	public ShopInfo getShopInfoFromLocalCache(Long shopId) {
		return null;
	}
	
	/**
	 * 将商品信息保存到redis中
	 * @param productInfo 
	 */
	public void saveProductInfo2RedisCache(ProductInfo productInfo) {
		SaveProductInfo2RedisCacheCommand command = new SaveProductInfo2RedisCacheCommand(productInfo);
		command.execute();
	}
	
	/**
	 * 将店铺信息保存到redis中
	 * @param productInfo 
	 */
	public void saveShopInfo2RedisCache(ShopInfo shopInfo) {
		SaveShopInfo2RedisCacheCommand command = new SaveShopInfo2RedisCacheCommand(shopInfo);
		command.execute();
	}
	
	/**
	 * 从redis中获取商品信息
	 * @param productInfo 
	 */
	public ProductInfo getProductInfoFromRedisCache(Long productId) {
		GetProductInfoFromRedisCacheCommand command = new GetProductInfoFromRedisCacheCommand(productId);
		return command.execute();
	}
	
	/**
	 * 从redis中获取店铺信息
	 * @param productInfo 
	 */
	public ShopInfo getShopInfoFromRedisCache(Long shopId) {
		GetShopInfoFromRedisCacheCommand command = new GetShopInfoFromRedisCacheCommand(shopId);
		return command.execute();
	}
	
}
