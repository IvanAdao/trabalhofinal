
-- Tabela: Utilizador
CREATE TABLE Utilizador (
    id_utilizador INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255),
    username VARCHAR(100) UNIQUE,
    senha VARCHAR(255),
    perfil ENUM('RH', 'AUDITOR', 'ADMIN'),
    email VARCHAR(255),
    activo BOOLEAN DEFAULT TRUE,
    ultimo_login DATETIME
);

-- Tabela: Servidor_Publico
CREATE TABLE Servidor_Publico (
    id_servidor INT PRIMARY KEY AUTO_INCREMENT,
    nome_completo VARCHAR(255),
    numero_bi VARCHAR(100) UNIQUE,
    nif VARCHAR(100),
    cargo VARCHAR(100),
    orgao VARCHAR(100),
    salario_base DECIMAL(10,2),
    estado ENUM('ACTIVO', 'SUSPENSO', 'INVESTIGACAO')
);

-- Tabela: Alteracoes_Folha
CREATE TABLE Alteracoes_Folha (
    id_alteracao INT PRIMARY KEY AUTO_INCREMENT,
    id_servidor INT,
    id_utilizador INT,
    campo_modificado VARCHAR(100),
    valor_anterior TEXT,
    valor_novo TEXT,
    motivo TEXT,
    data_alteracao DATETIME,
    ip_origem VARCHAR(50),
    alerta_activado BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id_servidor) REFERENCES Servidor_Publico(id_servidor),
    FOREIGN KEY (id_utilizador) REFERENCES Utilizador(id_utilizador)
);

-- Tabela: Investigacao
CREATE TABLE Investigacao (
    id_investigacao INT PRIMARY KEY AUTO_INCREMENT,
    id_servidor INT,
    data_abertura DATETIME,
    estado ENUM('EM_ANALISE', 'FECHADO', 'ARQUIVADO'),
    comentarios TEXT,
    FOREIGN KEY (id_servidor) REFERENCES Servidor_Publico(id_servidor)
);

-- Tabela: Alertas_IA
CREATE TABLE Alertas_IA (
    id_alerta INT PRIMARY KEY AUTO_INCREMENT,
    id_alteracao INT,
    tipo_alerta VARCHAR(100),
    risco ENUM('BAIXO', 'MÉDIO', 'ALTO'),
    data_detectado DATETIME,
    FOREIGN KEY (id_alteracao) REFERENCES Alteracoes_Folha(id_alteracao)
);

-- Tabela: Prova_De_Vida
CREATE TABLE Prova_De_Vida (
    id_prova INT PRIMARY KEY AUTO_INCREMENT,
    id_servidor INT,
    metodo ENUM('FACIAL', 'VOZ', 'EXTERNO_INE'),
    resultado ENUM('VALIDO', 'INVALIDO'),
    data_verificacao DATETIME,
    FOREIGN KEY (id_servidor) REFERENCES Servidor_Publico(id_servidor)
);

-- Tabela: Sessao
CREATE TABLE Sessao (
    id_sessao INT PRIMARY KEY AUTO_INCREMENT,
    id_utilizador INT,
    ip_acesso VARCHAR(50),
    localizacao VARCHAR(100),
    hora_login DATETIME,
    hora_logout DATETIME,
    FOREIGN KEY (id_utilizador) REFERENCES Utilizador(id_utilizador)
);

-- Tabela: Relatorio_Forense
CREATE TABLE Relatorio_Forense (
    id_relatorio INT PRIMARY KEY AUTO_INCREMENT,
    id_auditor INT,
    tipo_relatorio VARCHAR(50),
    parametros TEXT,
    data_exportacao DATETIME,
    FOREIGN KEY (id_auditor) REFERENCES Utilizador(id_utilizador)
);
