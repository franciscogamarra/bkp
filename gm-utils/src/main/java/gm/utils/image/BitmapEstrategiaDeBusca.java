package gm.utils.image;

/* as estretegias servem para tornar a busca mais rapida, pois eh comum sabermos mais ou menos onde a imagem deve estar */
public enum BitmapEstrategiaDeBusca {
	da_esquerda_para_direita_de_cima_para_baixo,
	da_direita_para_esquerda_de_cima_para_baixo,
	da_esquerda_para_direita_de_baixo_para_cima,
	da_direita_para_esquerda_de_baixo_para_cima,		
	de_cima_para_baixo_da_esquerda_para_direita,
	de_cima_para_baixo_da_direita_para_esquerda,
	de_baixo_para_cima_da_esquerda_para_direita,
	de_baixo_para_cima_da_direita_para_esquerda
}
