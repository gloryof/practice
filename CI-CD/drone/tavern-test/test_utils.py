from box import Box


def convert_csrf_token_to_json(response):
    return Box({"csrf_token": response.text})