# Tarefas

Este arquivo descreve as tarefas do projeto de controle de vacinas por bairro.

## Backend

### Criar endpoint de login

1. Criar endpoint de login em `UsuarioController` com a anotação `@PostMapping` para o caminho `/login`.
2. O endpoint deve receber um objeto `Usuario` como parâmetro.
3. O endpoint deve realizar uma consulta no banco de dados para encontrar um usuário com o nome de usuário e senha fornecidos.
4. Se o usuário for encontrado, o endpoint deve retornar o usuário para a página inicial. Caso contrário, o endpoint deve retornar uma mensagem de erro.

### Criar endpoint de cadastro de vacinas

1. Criar endpoint de cadastro de vacinas em `VacinaController` com a anotação `@PostMapping` para o caminho `/cadastrar`.
2. O endpoint deve receber um objeto `Vacina` como parâmetro.
3. O endpoint deve realizar uma inserção no banco de dados contendo o id da vacina, a data de aplicação, e o id do bairro.
4. Após concluída a operação, deve limpar os campos.
5. (Opcional) Mostrar mensagem de registro salvo.

### Criar endpoint de buscar relatório

1. Criar endpoint de listar por bairro em `RelatorioController` com a anotação `@GetMapping` para o caminho `/listar-por-bairros`.
2. O endpoint deve receber um objeto `Relatorio` com os campos que devem ser buscados as vacinas.
3. (Opcional) Caso não tenham nenhuma vacina naquele bairro, ainda deve exibir mensagem informando o usuário.
4. (Opcional) Caso o front envie os dados de dataInicial, dataFinal e doença alvo adicionar esses filtros na busca.

## Frontend

### Criar página de login

1. Criar uma página de login que chama o endpoint `/login` do backend.
2. A página deve exibir um formulário para o usuário inserir seu nome de usuário e senha.
3. A página deve enviar os dados do formulário para o endpoint `/login` do backend.

### Criar página de cadastro de vacinas

1. Criar uma página de cadastro de vacinas que chama o endpoint `/cadastrar` do backend.
2. A página deve exibir um formulário para o usuário inserir as informações da vacina.
3. A página deve enviar os dados do formulário para o endpoint `/cadastrar` do backend.

### Criar página de relatório

1. Criar uma página de relatório que chama o endpoint `/listar-por-bairros` do backend, passando uma lista com os ids dos bairros.
2. A página deve exibir uma lista de vacinas por bairro.
3. A página deve obter os dados da lista do endpoint `/listar-por-bairros` do backend.
4. (Opcional) enviar para o controller dataInicial, dataFinal e Doenca alvo da vacina.

## Prototipo

O protótipo de todas as telas está disponível no link abaixo:
https://www.figma.com/file/NWIDXrMATVasYz3ydkrByV/Untitled?type=design&node-id=1%3A4&mode=design&t=YAAYSnzwnRS6fs2K-1

## Observações

* Qualquer duvidas ou dificuldades podem pedir ajuda do pessoal do seu grupo

## Validação de ideias

* Página de relatório:
    * Validar ideia de a página de relatório ser uma listagem de vacina com filtros tendo a possibilidade de exportar para PDF os registros filtrados.
