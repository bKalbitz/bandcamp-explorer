package com.bkalbitz.bandcamp_item_detail.album;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bkalbitz.bandcamp_item_detail.album.data.AlbumDTO;
import com.bkalbitz.bandcamp_item_detail.album.data.ReommendedAlbumDTO;
import com.bkalbitz.bandcamp_item_detail.album.persistance.AlbumEntity;
import com.bkalbitz.bandcamp_item_detail.album.persistance.AlbumRepository;
import com.bkalbitz.bandcamp_item_detail.album.persistance.RecommendationEnitity;
import com.bkalbitz.bandcamp_item_detail.album.persistance.RecommendationId;
import com.bkalbitz.bandcamp_item_detail.album.persistance.RecommendationRespository;

@Service
public class AlbumService {

  @Autowired
  private AlbumRepository albumRepository;
  @Autowired
  private RecommendationRespository recommendationRespository;

  public AlbumDTO getAlbum(String url) {
    if (albumRepository.existsById(url)) {
      return new AlbumDTO(albumRepository.getReferenceById(url), recommendationRespository.findAllForAlbumUrl(url));
    }

    AlbumDTO result = new AlbumDTO();
    result.setUrl(url);
    try {
      Document doc = Jsoup.connect(url).get();
      result.setCollectionUrls(doc.select(".fan.pic").stream().map(e -> e.attr("href")).filter(StringUtils::isNotBlank)
          .map(e -> StringUtils.substring(e, 0, StringUtils.indexOf(e, '?'))).toList());
      result
          .setTags(doc.select(".tag").stream().map(e -> e.text()).filter(StringUtils::isNotBlank).distinct().toList());
      result.setRecomendetAlbums(
          doc.select(".recommended-album.footer-cc").stream().map(e -> toReommendedAlbumDTO(e)).toList());
      perists(result);
    } catch (IOException e) {
      return null;
    }
    return result;
  }

  private void perists(AlbumDTO result) {
    AlbumEntity entity = new AlbumEntity();
    entity.setUrl(result.getUrl());
    entity.setCollectionUrls(StringUtils.join(result.getCollectionUrls(), AlbumEntity.URL_SEPERATOR));
    entity.setTags(StringUtils.join(result.getTags(), AlbumEntity.URL_SEPERATOR));
    albumRepository.save(entity);
    for (ReommendedAlbumDTO recommended : result.getRecomendetAlbums()) {
      RecommendationEnitity recommendationEnitity = new RecommendationEnitity(
          new RecommendationId(result.getUrl(), recommended.getUrl()), recommended.getArtUrl());
      recommendationRespository.save(recommendationEnitity);
    }

  }

  private ReommendedAlbumDTO toReommendedAlbumDTO(Element e) {
    String img = e.select("img").first().attr("src");
    String url = e.select(".album-link").first().attr("href");
    url = StringUtils.substring(url, 0, StringUtils.indexOf(url, '?'));
    return new ReommendedAlbumDTO(url, img);
  }
}
