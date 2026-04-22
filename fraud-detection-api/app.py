from flask import Flask, request, jsonify
import pickle
import numpy as np

app = Flask(__name__)

# Load model
model = pickle.load(open('fraud_model.pkl', 'rb'))
url_model = pickle.load(open('url_model.pkl', 'rb'))

@app.route('/')
def home():
    return "Fraud Detection API Running"

@app.route('/predict', methods=['POST'])
def predict():
    try:
        data = request.json['features']
        
        # Convert to numpy array
        features = np.array(data).reshape(1, -1)
        
        prediction = model.predict(features)[0]
        probability = model.predict_proba(features)[0][1]

        return jsonify({
            'fraud': int(prediction),
            'probability': float(probability)
        })
    
    except Exception as e:
        return jsonify({'error': str(e)})
    

@app.route('/check_url', methods=['POST'])
def check_url():
    url = request.json['url']
    
    if "@" in url or "-" in url or len(url) > 50:
        return jsonify({"status": "Suspicious"})
    else:
        return jsonify({"status": "Safe"})



def extract_url_features(url):
    return [
        len(url),
        int("@" in url),
        int("https" in url),
        int("-" in url),
        url.count("."),
    ]


@app.route('/predict-url', methods=['POST'])
def predict_url():
    try:
        url = request.json['url']
        
        features = extract_url_features(url)
        features = np.array(features).reshape(1, -1)

        prediction = url_model.predict(features)[0]
        probability = url_model.predict_proba(features)[0][1]

        return jsonify({
            'prediction': int(prediction),
            'probability': float(probability)
        })

    except Exception as e:
        return jsonify({'error': str(e)})
    

    
if __name__ == '__main__':
    app.run(debug=True, port=5000)